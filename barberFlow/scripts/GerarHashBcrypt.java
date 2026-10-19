import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Gera o INSERT SQL de um usuário ADMIN com a senha já em BCrypt.
 * Uso: veja scripts/gerar-hash-admin.ps1 (a senha chega pela variável de ambiente BF_ADMIN_SENHA).
 */
public class GerarHashBcrypt {

    public static void main(String[] args) {
        String senha = System.getenv("BF_ADMIN_SENHA");
        if (args.length < 2 || senha == null || senha.length() < 8 || senha.length() > 72) {
            System.err.println("Informe nome e e-mail e use uma senha de 8 a 72 caracteres.");
            System.exit(1);
        }
        String nome = escapar(args[0]);
        String email = escapar(args[1].trim().toLowerCase());
        String hash = new BCryptPasswordEncoder().encode(senha);

        System.out.println("INSERT INTO usuario (nome, email, senha_hash, funcao, criado_em)");
        System.out.println("VALUES ('" + nome + "', '" + email + "', '" + hash + "', 'ADMIN', NOW());");
    }

    private static String escapar(String valor) {
        return valor.replace("\\", "\\\\").replace("'", "''");
    }
}
