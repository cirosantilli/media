#!/usr/bin/env bash
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
screenshot_dir="${SCRIPT_DIR}/curls"
rm -rf "$screenshot_dir"
mkdir -p "$screenshot_dir"
cd "$screenshot_dir"
while read url; do
  echo "$url"
  curl -vvv $url &> $url
done < <( awk -F, 'NR>1{ print $2 }' "$SCRIPT_DIR/hits.csv" )
