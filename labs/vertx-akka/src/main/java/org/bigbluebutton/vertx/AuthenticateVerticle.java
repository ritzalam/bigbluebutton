package org.bigbluebutton.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.bigbluebutton.VertxToAkkaGateway;

public class AuthenticateVerticle extends AbstractVerticle {
	  private final VertxToAkkaGateway gw;
	  
	  public AuthenticateVerticle(VertxToAkkaGateway gw) {
	    this.gw = gw;
	  }
	  
  @Override
  public void start() throws Exception {

    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

    // Simple auth service which uses a properties file for user/role info
    AuthProvider authProvider = new MyAuthProvider(vertx);


    // Any requests to URI starting '/private/' require login
    router.route("/private/*").handler(RedirectAuthHandler.create(authProvider, "/loginpage.html"));

    // Serve the static private pages from directory 'private'
    router.route("/private/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("private"));

    // Handles the actual login
    router.route("/loginhandler").handler(FormLoginHandler.create(authProvider));

    // Implement logout
    router.route("/logout").handler(context -> {
      context.clearUser();
      // Redirect back to the index page
      context.response().putHeader("location", "/").setStatusCode(302).end();
    });

    // Serve the non private static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router).listen(4000);
  }
}
