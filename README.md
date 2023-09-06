# Desafio Técnico Android

Este projeto Android foi desenvolvido como parte de um desafio técnico. O objetivo principal era criar um aplicativo em Java que consome três APIs diferentes de consulta de CEP e armazena os CEPs pesquisados pelo usuário em cache. 

<div align="center">
<img src="https://github.com/alini159/maps_project/assets/44498505/a5a6c59a-9eca-495d-ba12-d6f86335a20e" width="200px" />
<img src="https://github.com/alini159/maps_project/assets/44498505/42cffe87-c338-4e6b-86af-bcfeb474ac5f" width="200px" />
</div>

## Tecnologias Utilizadas 🚀

- Linguagem: Java
- Arquitetura: Clean Architecture com MVVM (Model-View-ViewModel)
- Bibliotecas:
  - Room: Banco de dados para armazenar os CEPs em cache.
  - Retrofit: Para fazer as chamadas às APIs de consulta de CEP.
  - OkHttp: Biblioteca de cliente HTTP para o Retrofit.
  - Fragment: Utilizado para a construção das telas do aplicativo.
  - LiveData: Para observação e atualização de dados na interface do usuário.
  - ViewModel: Responsável por gerenciar os dados exibidos nas telas.

## Funcionalidades ☑️

O aplicativo possui uma única tela com os seguintes elementos:

- Um campo de entrada para inserir o CEP.
- Um campo de exibição para mostrar todas as informações associadas ao retorno das APIs.

## Estratégia de Consulta de CEP 📚

A estratégia de consulta de CEP segue a seguinte ordem:

1. Primeiro, o aplicativo verifica se o CEP está armazenado no banco de dados local (Room). Se estiver, as informações são recuperadas do banco de dados e exibidas ao usuário.

2. Se o CEP não estiver no banco de dados, o aplicativo faz uma consulta à API ViaCep para obter as informações. 

3. No caso de a API ViaCep estar indisponível (ou qualquer outra situação de falha), o aplicativo tenta a API ApiCep, que está configurada como segunda opção.

4. Caso a API ApiCep também esteja indisponível (ou ocorra outra falha), o aplicativo faz uma última tentativa com a API AwesomeApi.

## Como Usar 🗺️

Para utilizar o aplicativo, siga os passos abaixo:

1. Abra o aplicativo no dispositivo Android.

2. Insira o CEP desejado no campo de entrada.

3. Clique em "Buscar por CEP".

4. As informações associadas ao CEP serão exibidas no campo de exibição.

## Observações 🌱

- Certifique-se de estar conectado à internet para permitir que o aplicativo consulte as APIs.

Este projeto foi desenvolvido como um exemplo de aplicativo Android que integra várias tecnologias e abordagens comuns em desenvolvimento Android.

