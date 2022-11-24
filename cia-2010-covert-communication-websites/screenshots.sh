#!/usr/bin/env bash
set -eu
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
screenshot_dir="${SCRIPT_DIR}/screenshots"
rm -rf "$screenshot_dir"
mkdir -p "$screenshot_dir"
cd "$screenshot_dir"
while read url; do
  echo "$url"
  pageres --filename="$(echo "$url" | sed -r 's/.*\/\///' | sed -r 's/\/$//')" $url
done < <(
  # Selected screenshots.
  echo "\
https://web.archive.org/web/20101230033220/http://starwarsweb.net/
https://web.archive.org/web/20110202091611/http://iranfootballsource.com/
https://web.archive.org/web/20101211095158/http://www.dedrickonline.com/
https://web.archive.org/web/20100514032916/http://lesummumdelafinance.com
https://web.archive.org/web/20110128162039/http://attivitaestremi.com/
https://web.archive.org/web/20101230165001/http://noticiasmusica.net/
https://web.archive.org/web/20110207210053/http://economicnewsbuzz.com/
https://web.archive.org/web/20110107022935/http://snapnewsfront.net/
https://web.archive.org/web/20101230191405/http://philippinenewsonline.net/
https://web.archive.org/web/20110202190838/http://feedsdemexicoyelmundo.com/
https://web.archive.org/web/20120218052121/http://www.easytraveleurope.com/
https://web.archive.org/web/20110107045521/http://tee-shot.net
https://web.archive.org/web/20110128173433/http://nouvellesetdesrapports.com/
https://web.archive.org/web/20110203201604/http://pangawana.com/
https://web.archive.org/web/20110202124633/http://recuerdosdeviajeonline.com/
https://web.archive.org/web/20101226182928/http://theworld-news.net/
https://web.archive.org/web/20100520013854/http://www.kessingerssportsnews.com/
https://web.archive.org/web/20110207202401/http://negativeaperture.com/
"
)
for f in *; do
  convert "$f" "${f%.png}.jpg"
done
rm *.png
# Screenshot all.
#done < <( awk -F, 'NR>1{ print $3 }' "$SCRIPT_DIR/hits.csv" )
