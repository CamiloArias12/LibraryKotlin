package com.example.librariproject.data.author

import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.AuthorCreateMutation
import com.example.librariproject.AuthorDeleteQuery
import com.example.librariproject.AuthorUpdateMutation
import com.example.librariproject.GetAuthorQuery
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.type.AuthorInput

class  AuthorData{

   suspend fun  updateAuthor(id:Int, name:String, country:String, biography:String): AuthorUpdateMutation.AuthorUpdate? {
      val response:ApolloResponse<AuthorUpdateMutation.Data> = apolloClient.mutation(AuthorUpdateMutation(
         AuthorInput(id = id.toDouble(), name = name, nationality = country, biography=biography)
      )).execute()
      return response.data?.authorUpdate
   }

   suspend fun  getAuthor(id: Int): GetAuthorQuery.Author? {
      val response: ApolloResponse<GetAuthorQuery.Data> = apolloClient.query(GetAuthorQuery(authorId = id.toDouble())).execute()
      return response.data?.author
   }

   suspend fun  createAuthor(id:Int, name:String, country:String, biography:String): AuthorCreateMutation.AuthorCreate? {
      val response:ApolloResponse<AuthorCreateMutation.Data> = apolloClient.mutation(AuthorCreateMutation(
         AuthorInput(id = id.toDouble(), name = name, nationality = country, biography=biography)
      )).execute()
      return response.data?.authorCreate
   }

   suspend fun  deleteAuthor(id: Int): Boolean {
      val  response: ApolloResponse<AuthorDeleteQuery.Data> = apolloClient.query(AuthorDeleteQuery(authorId = id.toDouble())).execute()
      return response.data?.authorDelete!!
   }

}