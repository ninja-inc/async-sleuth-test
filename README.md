# async-sleuth-test

Reproduce procedure

Tracing is unavailable in ListenableFuture's callbacks

`curl http://localhost:8080/trace-async-rest-template`

Tracing is available in ListenableFuture's callbacks with sleep

`curl http://localhost:8080/trace-async-rest-template?isSleep=true`
