# Débitos

* [API sem autenticação e autorização](sem-autenticacao.md) - Qualquer cliente que alcance a API pode ler e alterar todos os dados.
* [Deploy da API e do app precisa ser conjunto](deploy-conjunto.md) - O deploy antigo (Elastic Beanstalk e GitHub Pages) não acompanha o novo contrato de erro, página e idioma.
* [Sem scan automatizado de dependências](scan-de-dependencias.md) - Não há verificação automática de vulnerabilidades nas dependências Maven nem em PR.
* [Virtual threads sem teste que prove o uso](virtual-threads-sem-teste.md) - A propriedade spring.threads.virtual.enabled está ligada, mas nenhum teste confirma que as requisições rodam em virtual threads.
