<#
.SYNOPSIS
  Gera o comando SQL para criar o usuário ADMIN (seed manual) com a senha em BCrypt.

.EXAMPLE
  .\scripts\gerar-hash-admin.ps1 -Nome "Maria Admin" -Email "maria@barbearia.com"
  (a senha é pedida no terminal e não aparece na tela)
#>
param(
    [Parameter(Mandatory = $true)][string]$Nome,
    [Parameter(Mandatory = $true)][string]$Email,
    [securestring]$Senha
)

$raiz = Split-Path -Parent $PSScriptRoot
Set-Location $raiz

if (-not $Senha) { $Senha = Read-Host -AsSecureString 'Senha do ADMIN (8 a 72 caracteres)' }
$ptr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($Senha)
try { $env:BF_ADMIN_SENHA = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($ptr) }
finally { [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($ptr) }

$arquivoCp = Join-Path ([IO.Path]::GetTempPath()) 'barberflow-classpath.txt'
try {
    & .\mvnw.cmd -q dependency:build-classpath "-Dmdep.outputFile=$arquivoCp" | Out-Null
    $cp = (Get-Content $arquivoCp -Raw).Trim()
    java -cp $cp scripts\GerarHashBcrypt.java $Nome $Email
}
finally {
    Remove-Item Env:\BF_ADMIN_SENHA -ErrorAction SilentlyContinue
    Remove-Item $arquivoCp -ErrorAction SilentlyContinue
}
