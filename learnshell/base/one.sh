#!/usr/bin/env bash

cat >hello.sh <<\EOF
#!/bin/sh
echo "Hello World!"
EOF

chmod +x hello.sh
./hello.sh