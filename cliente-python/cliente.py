import argparse
import json
import os
from typing import Any

import requests

BASE_URL = os.getenv("API_BASE_URL", "http://localhost:8080")


def print_json(data: Any) -> None:
    print(json.dumps(data, indent=2, ensure_ascii=False))


def listar_espacos() -> None:
    response = requests.get(f"{BASE_URL}/espacos", timeout=10)
    response.raise_for_status()
    print_json(response.json())


def criar_espaco(nome: str, tipo: str, capacidade: int) -> None:
    payload = {
        "nome": nome,
        "tipo": tipo,
        "capacidade": capacidade,
    }
    response = requests.post(f"{BASE_URL}/espacos", json=payload, timeout=10)
    response.raise_for_status()
    print("Espaço criado com sucesso.")


def listar_reservas() -> None:
    response = requests.get(f"{BASE_URL}/reservas", timeout=10)
    response.raise_for_status()
    print_json(response.json())


def criar_reserva(data: str, usuario_id: int, espaco_id: int) -> None:
    payload = {
        "data": data,
        "usuarioId": usuario_id,
        "espacoId": espaco_id,
    }
    response = requests.post(f"{BASE_URL}/reservas", json=payload, timeout=10)
    response.raise_for_status()
    print("Reserva criada com sucesso.")


def listar_usuarios() -> None:
    response = requests.get(f"{BASE_URL}/usuarios", timeout=10)
    response.raise_for_status()
    print_json(response.json())


def criar_usuario(usuario_id: int, nome: str) -> None:
    payload = {
        "id": usuario_id,
        "nome": nome,
    }
    response = requests.post(f"{BASE_URL}/usuarios", json=payload, timeout=10)
    response.raise_for_status()
    print("Usuário criado com sucesso.")


def main() -> None:
    parser = argparse.ArgumentParser(description="Cliente Python para a API de reservas")
    subparsers = parser.add_subparsers(dest="command", required=True)

    subparsers.add_parser("listar-espacos", help="Lista os espaços físicos")

    criar_espaco_parser = subparsers.add_parser("criar-espaco", help="Cria um espaço físico")
    criar_espaco_parser.add_argument("--nome", required=True)
    criar_espaco_parser.add_argument("--tipo", choices=["Sala", "Laboratorio"], required=True)
    criar_espaco_parser.add_argument("--capacidade", type=int, required=True)

    subparsers.add_parser("listar-reservas", help="Lista as reservas")

    criar_reserva_parser = subparsers.add_parser("criar-reserva", help="Cria uma reserva")
    criar_reserva_parser.add_argument("--data", required=True)
    criar_reserva_parser.add_argument("--usuario-id", type=int, required=True)
    criar_reserva_parser.add_argument("--espaco-id", type=int, required=True)

    subparsers.add_parser("listar-usuarios", help="Lista os usuários")

    criar_usuario_parser = subparsers.add_parser("criar-usuario", help="Cria um usuário")
    criar_usuario_parser.add_argument("--id", type=int, required=True)
    criar_usuario_parser.add_argument("--nome", required=True)

    args = parser.parse_args()

    if args.command == "listar-espacos":
        listar_espacos()
    elif args.command == "criar-espaco":
        criar_espaco(args.nome, args.tipo, args.capacidade)
    elif args.command == "listar-reservas":
        listar_reservas()
    elif args.command == "criar-reserva":
        criar_reserva(args.data, args.usuario_id, args.espaco_id)
    # --- NOVOS COMANDOS EXECUTADOS ---
    elif args.command == "listar-usuarios":
        listar_usuarios()
    elif args.command == "criar-usuario":
        criar_usuario(args.id, args.nome)


if __name__ == "__main__":
    main()
