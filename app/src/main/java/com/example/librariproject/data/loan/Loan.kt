package com.example.librariproject.data.loan

import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.DeleteBookQuery
import com.example.librariproject.DeleteLoanQuery
import com.example.librariproject.GetAllLoanQuery
import com.example.librariproject.GetUserQuery
import com.example.librariproject.LoanCreateMutation
import com.example.librariproject.LoanQuery
import com.example.librariproject.LoanUpdateMutation
import com.example.librariproject.UserUpdateMutation
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.type.LoanInput
import com.example.librariproject.type.LoanInputUpdate
import com.example.librariproject.type.UserInput

class LoanData(){

    suspend fun  deleteLoan(id: Int): Boolean {
        val  response: ApolloResponse<DeleteLoanQuery.Data> = apolloClient.query(DeleteLoanQuery( loanId = id.toDouble())).execute()
        return response.data?.loanDelete!!
    }

    suspend fun  getLoan(id:Int): LoanQuery.Loan? {
        val response: ApolloResponse<LoanQuery.Data> = apolloClient.query(LoanQuery(loanId = id.toDouble())).execute()
        return response.data?.loan
    }
    suspend fun  updateLoan(id: Int,bookId:Int,returnDate:String,returned:Boolean,startDate:String): LoanUpdateMutation.LoanUpdate? {
        val response : ApolloResponse<LoanUpdateMutation.Data> = apolloClient.mutation( LoanUpdateMutation(
            LoanInputUpdate(id = id.toDouble(), bookId = bookId.toDouble(), returnDate =returnDate, startDate =startDate, returned = returned  ))).execute()
        return response.data?.loanUpdate
    }
    suspend fun  createLoan(userId:Int,bookId: Int,returnDate:String,startDate:String): LoanCreateMutation.LoanCreate? {
        val response : ApolloResponse<LoanCreateMutation.Data> = apolloClient.mutation( LoanCreateMutation(
            LoanInput(userId = userId.toDouble(), bookId = bookId.toDouble(), returnDate =returnDate, startDate =startDate )
        )).execute()
        return response.data?.loanCreate
    }

}