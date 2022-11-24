#!/usr/bin/env bash
set -e
html_dir="html"
mkdir -p "$html_dir"
jq -r '.[].archive' hits.json | grep '^https://web.archive.org' | sort | while read archive; do
  domain="$(printf '%s' "$archive" | sed -r 's/(:[0-9]+)?\/$//;s/.*\///;s/^www\.//')"
  echo "$domain"
  domain_dir="${html_dir}/${domain}"
  mkdir -p "$domain_dir"
  index="${domain_dir}/index.html"
  if ! [ -s "$index" ]; then
    set +e
    response="$(timeout 10 curl --connect-timeout 10 -w "\n%{http_code}" "$archive")"
    set -e
    if [ "$?" -ne 0 ]; then
      echo "error: $response"
    else
      http_code="$(tail -n1 <<< "$response")"
      content="$(sed '$ d' <<< "$response")"
      if [ 200 -eq "$http_code" ]; then
        printf '%s' "$content" > "$index"
      else
        echo "error: http code: $http_code response: $response"
      fi
    fi
    sleep 1
  fi
done
