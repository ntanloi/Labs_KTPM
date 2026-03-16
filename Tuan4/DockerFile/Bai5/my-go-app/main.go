package main

import (
    "fmt"
    "net/http"
    "log"
)

func handler(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintln(w, "Hello from Go app! 🚀")
}

func main() {
    http.HandleFunc("/", handler)
    log.Println("Server running on port 8080...")
    log.Fatal(http.ListenAndServe(":8080", nil))
}