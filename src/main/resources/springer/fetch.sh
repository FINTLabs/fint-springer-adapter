#!/bin/bash

# curl -s https://play-with-fint.felleskomponent.no/administrasjon/personal/person | jq '._class = "no.fint.model.resource.felles.PersonResource"' > person.json
# curl -s https://play-with-fint.felleskomponent.no/administrasjon/personal/personalressurs | jq '._class = "no.fint.model.resource.administrasjon.personal.PersonalressursResource"' > personalressurs.json
# curl -s https://play-with-fint.felleskomponent.no/administrasjon/personal/arbeidsforhold | jq '._class = "no.fint.model.resource.administrasjon.personal.ArbeidsforholdResource"' > arbeidsforhold.json
# curl -s https://play-with-fint.felleskomponent.no/administrasjon/organisasjon/organisasjonselement | jq '._class = "no.fint.model.resource.administrasjon.organisasjon.OrganisasjonselementResource"' > organisasjonselement.json

curl -s https://play-with-fint.felleskomponent.no/$1 | sed -e 's#https://play-with-fint.felleskomponent.no#${}#g' | jq '._class = "no.fint.model.resource.'$2'Resource"'
