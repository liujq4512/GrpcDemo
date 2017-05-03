package com.client;

import com.dianrong.accounting.processor.message.proto.jobresult.Hello;
import com.dianrong.accounting.processor.message.proto.jobresult.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;


/**
 * Created by jinqiang.liu on 17-5-2.
 */
public class HelloClient {



    public static void main(String[] args)throws Exception{
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("127.0.0.1",8888)
                .usePlaintext(true)
                .build();

        try {
            HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);
            System.out.println("=========start to say hello in client=======================");

            System.out.println(">>>>>>" +
                    stub.sayHello(Hello.JobResultRequest.newBuilder().setName("client" ).build()));
        }finally {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }



    }


}


