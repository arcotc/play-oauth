// Copyright (C) 2011-2017 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package controllers

import javax.inject._

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import play.api.mvc._
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.{DataStoreFactory, MemoryDataStoreFactory}
import com.google.api.services.oauth2.Oauth2

@Singleton
class AuthenticationController @Inject() extends Controller {

  import java.util

  val APPLICATION_NAME = "play-oauth"

  private val SCOPES = util.Arrays.asList(
    "https://www.googleapis.com/auth/userinfo.profile",
    "https://www.googleapis.com/auth/userinfo.email"
  )

  val JSON_FACTORY = JacksonFactory.getDefaultInstance()

  def signUpRedirect: Action[AnyContent] = Action { implicit request =>
    val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val dataStoreFactory = MemoryDataStoreFactory
    val credential = authorize(httpTransport, dataStoreFactory)

    val oauth2 = new Oauth2
      .Builder(httpTransport, JSON_FACTORY, credential)
      .setApplicationName(APPLICATION_NAME).build()

    Ok(views.html.index("Play OAuth"))
  }

  def authorize(httpTransport: NetHttpTransport, dataStoreFactory: DataStoreFactory) = {
    import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
    import java.io.InputStreamReader

    val clientSecrets = GoogleClientSecrets.load(
      JSON_FACTORY,
      new InputStreamReader(classOf[Nothing].getResourceAsStream("/client_secrets.json"))
    )

    val flow: GoogleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
      httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(
      dataStoreFactory).build()

    new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
  }
}
