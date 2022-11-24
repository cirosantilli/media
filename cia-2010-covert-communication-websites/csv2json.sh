awk -F, 'BEGIN{printf("[")} END{printf("\n]")} { printf("\n{\n  \"ip\": \"%s\",\n  \"host\": \"%s\",\n  \"archive\": \"%s\",\n  \"comms\": \"%s\"\n},",$1,$2,$3,$4) }' hits.csv
