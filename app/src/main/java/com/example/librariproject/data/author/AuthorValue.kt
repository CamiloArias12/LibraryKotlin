package com.example.librariproject.data.author

import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.AuthorUpdateMutation
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.type.AuthorInput

class  AuthorValue{

   suspend fun  updateAuthor(id:Int, name:String, country:String, biography:String){
      val response:ApolloResponse<AuthorUpdateMutation.Data> = apolloClient.mutation(AuthorUpdateMutation(
         AuthorInput(id = id.toDouble(), name = name, nationality = country, biography=biography)
      )).execute()

   }

}