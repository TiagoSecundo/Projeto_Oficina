# 🚗 Sistema de Gestão para Oficina - Milho Verde
⚠️ Projeto em desenvolvimento - não está finalizado ainda.
Este repositório contém o desenvolvimento de um sistema de gestão para uma oficina localizada em Milho Verde, proposto como continuação do Trabalho Prático Integrador (TPI). O objetivo é organizar e automatizar os processos da oficina, que conta com três elevadores (sendo um exclusivo para alinhamento e balanceamento), uma loja de peças e uma equipe de colaboradores.

📌 Funcionalidades previstas
O sistema será responsável por gerenciar:

🛠️ Serviços realizados (com status: recebido, em manutenção, pronto, entregue);

📅 Agendamentos de manutenção com verificação de disponibilidade;

👤 Cadastro e edição de clientes, com CPF pseudo-anonimizado;

🚗 Associação de veículos a clientes, serviços e mecânicos;

🧾 Emissão de nota fiscal contendo serviços e peças utilizadas;

🧑‍🔧 Controle de colaboradores (cadastro, edição e autenticação);

🛒 Controle de estoque da loja de peças e atualizações com fornecedores;

💸 Controle de despesas (materiais, limpeza, funcionários etc.);

📈 Geração de relatórios de vendas e balanços mensais;

🔐 Controle de acesso por autenticação (restrição de dados financeiros aos administradores).

✅ Requisitos e critérios do projeto (resumo)
Diagrama de casos de uso e fluxos de eventos para todos os casos obrigatórios;

Diagrama de classes completo, com atributos e métodos;

Implementação com encapsulamento, construtores e sobrescrita de métodos;

Uso de super, static, herança e interface Comparator;

Salvamento e recuperação de dados em arquivos .json;

Geração de extratos por cliente;

JavaDoc da aplicação;

📂 Estrutura do Projeto
O projeto está organizado conforme boas práticas de separação de responsabilidades (inspirado no padrão MVC), incluindo pacotes para:

entities: entidades do sistema como Cliente, Veículo, Agendamento, etc.

services: lógica de negócio e controle do sistema.

program: execução principal com testes e chamadas de funcionalidades.

Este repositório será atualizado conforme o projeto evolui. Sinta-se à vontade para acompanhar o progresso e contribuir com sugestões!
