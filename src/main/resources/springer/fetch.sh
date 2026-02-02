#!/bin/bash

# Example usage: `./fetch.sh utdanning/elev/klasse`
# This will fetch the klasse resource and save it to `utdanning.elev/klasse.json`

env=play-with-fint

curl -s https://${env}.felleskomponent.no/$1 \
  | sed -e 's#https://'${env}'.felleskomponent.no/\([^/]*\)/\([^/]*\)/\([^/]*\)/#${\1.\2.\3}/#g' \
  | jq "._class = \"no.novari.fint.model.resource.${2}Resource\"" \
  > "$(dirname "$1" | tr '/' '.')/$(basename "$1").json"
