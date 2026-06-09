import requests

url = "http://localhost:8080/espacos"

dados = {
    "nome": "Laboratório de Informática",
    "tipo": "Laboratorio",
    "capacidade": 30
}

response = requests.post(url, json=dados)

print(f"Status: {response.status_code}")

if response.text:
    print(response.text)