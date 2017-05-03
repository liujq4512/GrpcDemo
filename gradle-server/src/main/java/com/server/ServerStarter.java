package com.server;

import com.dianrong.accounting.processor.message.proto.jobresult.Hello;
import com.dianrong.accounting.processor.message.proto.jobresult.HelloServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Created by jinqiang.liu on 17-5-2.
 */
public class ServerStarter {

    private static Server server;

    public static void main(String[] args) throws Exception{

        server = ServerBuilder
                .forPort(8888)
                .addService(new HelloServerImpl())
                .build();

        server.start();

        System.out.println("server is started");

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("=========shutting down GRPC server since JVM is shutting down");
                if(server!=null){
                    server.shutdown();
                }
            }
        });

        server.awaitTermination();

    }



    static class HelloServerImpl extends HelloServiceGrpc.HelloServiceImplBase
    {

        public void sayHello(Hello.JobResultRequest request, StreamObserver<Hello.JobResultResponse> responseObserver) {
            System.out.println("start execute in server");
            doSomething(request.getName());

            responseObserver.onNext(Hello.JobResultResponse.newBuilder().setName("response to client").build());
            responseObserver.onCompleted();
        }


        public void doSomething(String name){
            System.out.println("hello " + name);
        }
    }
}
