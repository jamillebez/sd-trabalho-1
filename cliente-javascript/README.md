# Cliente JavaScript

Cliente CLI em Node.js para consumir a API REST Spring Boot. Usa `fetch` nativo, portanto requer Node.js 18 ou superior.

## Exemplos

```bash
node cliente.js listar-espacos
node cliente.js criar-espaco --nome "Sala 01" --tipo Sala --capacidade 40
node cliente.js listar-reservas
node cliente.js criar-reserva --data "2026-06-09T10:00" --usuario-id 1 --espaco-id 1
```

Use `API_BASE_URL` para apontar para outro servidor:

```bash
API_BASE_URL=http://localhost:8080 node cliente.js listar-espacos
```
