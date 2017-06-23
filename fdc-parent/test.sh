

while ! exec 3<>/dev/tcp/199.168.99.100/5672; do
    echo "Trying to connect to MySQL at 33060..."
    sleep 10
done