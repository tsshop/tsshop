package com.shop.tsshop.web.netty;

import com.shop.tsshop.web.netty.handler.HeartBeatHandler;
import com.shop.tsshop.web.netty.handler.HttpHandler;
import com.shop.tsshop.web.netty.handler.WebSocketDispatcherHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/13
 */
@Slf4j
public class NettyServer {

    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public void startServer(int port) {
        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 可以给每条连接设置一些TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 心跳机制
                .childOption(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    // 读写消息的处理逻辑
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // ChannelInboundHandler 处理读逻辑

                        // Http消息处理
                        ch.pipeline().addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS)); // 空闲检测,默认60秒
                        ch.pipeline().addLast(new HttpServerCodec()); // Http编码
                        ch.pipeline().addLast(new HttpObjectAggregator(64 * 1024)); // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse,原因是HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
                        ch.pipeline().addLast(new ChunkedWriteHandler());// 主要用于处理大数据流，比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的; 增加之后就不用考虑这个问题了
                        ch.pipeline().addLast(new HttpHandler());

                        // WebSocket消息处理
                        ch.pipeline().addLast(new WebSocketServerCompressionHandler()); // Websocket 数据压缩
                        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 10 * 1024)); // 支持websocket协议，数据包最大长度10k
                        ch.pipeline().addLast(new WebSocketDispatcherHandler());
                        ch.pipeline().addLast(new HeartBeatHandler()); // 心跳检测

                        // ChannelOutboundHandler 处理写逻辑
                        // ...
                    }
                })

                // 不常用方法
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    // handler() 通常用与服务端启动的过程中
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        log.info("服务端启动中");
                    }
                })

                .bind(port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("绑定端口[{}]成功", port);
                    } else {
                        log.error("绑定端口[{}]失败", port);
                    }
                });
    }

}
