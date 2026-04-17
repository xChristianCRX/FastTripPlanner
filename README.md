# FastTripPlanner ✈️

O **FastTripPlanner** é um aplicativo Android desenvolvido em Kotlin, cujo objetivo é auxiliar usuários no planejamento financeiro de suas viagens. Este projeto foi desenvolvido para a disciplina de desenvolvimento móvel, aplicando conceitos de navegação entre Activities, Intents e gerenciamento de estado.

## 📋 Especificações do Projeto

### 1. Objetivo
Integrar os conceitos de desenvolvimento móvel Android através de um fluxo de três telas, garantindo a validação de dados, persistência de estado e correta aplicação de regras de negócio.

### 2. Funcionalidades

#### **Tela 1: Dados da Viagem**
- Campos para destino, número de dias e orçamento diário.
- Validação de dados (impede campos vazios ou inválidos).
- Navegação via Intent Explícita.

#### **Tela 2: Opções da Viagem**
- Seleção de nível de hospedagem (Econômica, Conforto, Luxo).
- Escolha de serviços extras: Transporte, Alimentação e Passeios.
- Botões de cálculo e retorno.

#### **Tela 3: Resumo da Viagem**
- Exibição de todos os dados selecionados.
- Cálculo do valor total estimado.
- Opção de reiniciar o fluxo de planejamento.

### 3. Regras de Cálculo 🧮

O cálculo final segue a estrutura:
- **Custo Base:** `Número de Dias * Orçamento Diário`
- **Multiplicador de Hospedagem:**
  - Econômica: 1.0x
  - Conforto: 1.5x
  - Luxo: 2.2x
- **Serviços Extras:**
  - Transporte: + R$ 300,00 (Taxa única)
  - Alimentação: + R$ 50,00 por dia
  - Passeios: + R$ 120,00 por dia

## 🛠️ Tecnologias e Requisitos

- **Linguagem:** [Kotlin](https://kotlinlang.org/)
- **IDE:** Android Studio
- **SDK Mínimo:** Android 8.0 (API 26)
- **Restrição:** Sem uso de bibliotecas externas ou banco de dados.

## 📦 Como Instalar

1. Clone o repositório em sua máquina local.
2. Abra o projeto no **Android Studio**.
3. Sincronize o Gradle e compile o projeto.
4. Execute em um emulador ou dispositivo físico.
