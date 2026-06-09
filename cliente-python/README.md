# Cliente Python

Cliente CLI em Python para consumir a API REST Spring Boot.

## Instalação

```bash
pip install -r requirements.txt
```

## Exemplos

```bash
python cliente.py listar-espacos
python cliente.py criar-espaco --nome "Sala 01" --tipo Sala --capacidade 40
python cliente.py listar-reservas
python cliente.py criar-reserva --data "2026-06-09T10:00" --usuario-id 1 --espaco-id 1
```

Use `API_BASE_URL` para apontar para outro servidor:

```bash
API_BASE_URL=http://localhost:8080 python cliente.py listar-espacos
```
