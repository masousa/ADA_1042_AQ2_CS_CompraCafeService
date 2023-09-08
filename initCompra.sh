curl -X 'POST' \
  'http://localhost:9080/' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "items": [
    {
      "quantidade": 2,
      "identificadorItem": "a9c6fd11-b88d-4cda-b945-881f6df6958c",
      "combo": true
    },
    {
      "quantidade": 1,
      "identificadorItem": "13530e8e-6aab-49f8-ba24-aea4ce68cfc2",
      "combo": false
    }
  ],
  "formaPagamento": {
    "tipoPagamento": "PIX"
  },
  "identificador_cliente": "0a3abee5-ea4a-4306-95d2-d8aa343abd1a"
}'