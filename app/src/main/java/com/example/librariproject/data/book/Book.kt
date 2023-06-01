package com.example.librariproject.data.book

import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.AuthorDeleteQuery
import com.example.librariproject.BookCreateMutation
import com.example.librariproject.DeleteBookQuery
import com.example.librariproject.GetAuthorQuery
import com.example.librariproject.GetBookQuery
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.type.BookInput


class BookData{

    suspend fun  createBook(title:String, publisher:String,area:String, summary:String,authors:List<Int>): BookCreateMutation.BookCreate? {
        val response :ApolloResponse<BookCreateMutation.Data> = apolloClient.mutation(BookCreateMutation(
            create = BookInput(title = title, area = area, publisher = publisher, summary = summary),
            authors = authors)).execute()
        return response.data?.bookCreate
    }
    suspend fun  getBook(id: Int): GetBookQuery.GetBook? {
        val response: ApolloResponse<GetBookQuery.Data> = apolloClient.query(GetBookQuery(idBook = id.toDouble())).execute()
        return response.data?.getBook
    }
    suspend fun  deleteBook(id: Int): Boolean {
        val  response: ApolloResponse<DeleteBookQuery.Data> = apolloClient.query(DeleteBookQuery(idBook = id.toDouble())).execute()
        return response.data?.deleteBook!!
    }

}