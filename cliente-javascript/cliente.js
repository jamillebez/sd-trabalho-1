const BASE_URL = process.env.API_BASE_URL || "http://localhost:8080";

function printJson(data) {
  console.log(JSON.stringify(data, null, 2));
}

async function request(path, options = {}) {
  const response = await fetch(`${BASE_URL}${path}`, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
    ...options,
  });

  if (!response.ok) {
    const body = await response.text();
    throw new Error(`HTTP ${response.status}: ${body || response.statusText}`);
  }

  const text = await response.text();
  return text ? JSON.parse(text) : null;
}

async function listarEspacos() {
  const espacos = await request("/espacos");
  printJson(espacos);
}

async function criarEspaco(nome, tipo, capacidade) {
  await request("/espacos", {
    method: "POST",
    body: JSON.stringify({ nome, tipo, capacidade: Number(capacidade) }),
  });
  console.log("Espaço criado com sucesso.");
}

async function listarReservas() {
  const reservas = await request("/reservas");
  printJson(reservas);
}

async function criarReserva(data, usuarioId, espacoId) {
  await request("/reservas", {
    method: "POST",
    body: JSON.stringify({
      data,
      usuarioId: Number(usuarioId),
      espacoId: Number(espacoId),
    }),
  });
  console.log("Reserva criada com sucesso.");
}

function usage() {
  console.log(`Uso:
  node cliente.js listar-espacos
  node cliente.js criar-espaco --nome "Sala 01" --tipo Sala --capacidade 40
  node cliente.js listar-reservas
  node cliente.js criar-reserva --data "2026-06-09T10:00" --usuario-id 1 --espaco-id 1

Variável opcional:
  API_BASE_URL=http://localhost:8080`);
}

function getOption(args, name) {
  const index = args.indexOf(name);
  if (index === -1 || index === args.length - 1) {
    return undefined;
  }
  return args[index + 1];
}

async function main() {
  const [command, ...args] = process.argv.slice(2);

  if (command === "listar-espacos") {
    await listarEspacos();
    return;
  }

  if (command === "criar-espaco") {
    const nome = getOption(args, "--nome");
    const tipo = getOption(args, "--tipo");
    const capacidade = getOption(args, "--capacidade");

    if (!nome || !tipo || !capacidade) {
      usage();
      process.exitCode = 1;
      return;
    }

    await criarEspaco(nome, tipo, capacidade);
    return;
  }

  if (command === "listar-reservas") {
    await listarReservas();
    return;
  }

  if (command === "criar-reserva") {
    const data = getOption(args, "--data");
    const usuarioId = getOption(args, "--usuario-id");
    const espacoId = getOption(args, "--espaco-id");

    if (!data || !usuarioId || !espacoId) {
      usage();
      process.exitCode = 1;
      return;
    }

    await criarReserva(data, usuarioId, espacoId);
    return;
  }

  usage();
  process.exitCode = 1;
}

main().catch((error) => {
  console.error(error.message);
  process.exitCode = 1;
});
