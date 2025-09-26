Nicolas Souza dos Santos - rm555571
Oscar Arias Neto - rm556936
Julia Martins Rebelles - rm554516

Testes endpoints:
POST:

1- patios: http://localhost:8080/patios
{
  "nome": "Pátio Central"
}

2- operadores: http://localhost:8080/operadores
{
  "nome": "João Silva",
  "patioId": 1
}

3- automovel: http://localhost:8080/automoveis
{
  "placa": "ABC1234",
  "chassi": "9BWZZZ377VT004251",
  "tipo": "Scooter",
  "cor": "Vermelha",
  "localizacao": "Setor A - Vaga 12",
  "comentarios": "Moto com arranhões leves",
  "patioId": 1
}

GET:
-Listar todos os pátios
GET http://localhost:8080/patios

-Buscar pátio por ID
GET http://localhost:8080/patios/1

-Listar todos os operadores
GET http://localhost:8080/operadores

-Buscar operador por ID
GET http://localhost:8080/operadores/1

-Listar todos os automóveis (com paginação e ordenação)
GET http://localhost:8080/automoveis?page=0&size=10&sort=placa,asc

-Buscar automóvel por ID
GET http://localhost:8080/automoveis/1

-Buscar automóveis por placa (ex: ABC1234)
GET http://localhost:8080/automoveis?placa=ABC1234

PUT:
-Atualizar pátio
PUT http://localhost:8080/patios/1
Body (JSON):
{
  "nome": "Pátio Norte"
}

-Atualizar operador
PUT http://localhost:8080/operadores/1
Body (JSON):
{
  "nome": "João Mendes",
  "patioId": 1
}

-Atualizar automóvel
PUT http://localhost:8080/automoveis/1
Body (JSON):
{
  "placa": "DEF5678",
  "chassi": "9BWZZZ377VT004252",
  "tipo": "Trail",
  "cor": "Preta",
  "localizacao": "Setor B - Vaga 5",
  "comentarios": "Nova, sem observações",
  "patioId": 1
}
