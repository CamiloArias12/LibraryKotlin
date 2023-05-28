
package com.example.librariproject.data.user

import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.GetUserAllQuery
import com.example.librariproject.GetUserQuery
import com.example.librariproject.UpdateAdminMutation
import com.example.librariproject.UserCreateMutation
import com.example.librariproject.UserLoginMutation
import com.example.librariproject.UserUpdateMutation
import com.example.librariproject.adapter.GetUserAllQuery_ResponseAdapter
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.type.UserInput
import com.example.librariproject.type.UserLoginInput
import com.example.librariproject.type.UserUpdateAdmin

class UserData {

    suspend fun  getUsers(): List<GetUserAllQuery.UsersAll>? {
        val response: ApolloResponse<GetUserAllQuery.Data> = apolloClient.query(GetUserAllQuery()).execute()
        return response.data?.usersAll
    }
    suspend fun  getUser(id:Int): GetUserQuery.Users? {
        val response: ApolloResponse<GetUserQuery.Data> = apolloClient.query(GetUserQuery(userId = id.toDouble())).execute()
        return response.data?.users
    }
    suspend fun loginUser(email:String,password:String): UserLoginMutation.UserValidate? {
        val response : ApolloResponse<UserLoginMutation.Data> = apolloClient.mutation(UserLoginMutation(
            UserLoginInput(email=email, password = password)
        )).execute()
        return response.data?.userValidate
    }
    suspend fun  createUser(id: Int,firstName:String,lastName:String,address:String,phone:String,email: String,password: String): UserCreateMutation.UserCreate? {
        val response : ApolloResponse<UserCreateMutation.Data> = apolloClient.mutation(UserCreateMutation(
            UserInput(id = id.toDouble(), firstName = firstName, lastName = lastName, phone = phone, address = address,
                email=email, password = password))).execute()
        return response.data?.userCreate
    }
    suspend fun  updateUser(id: Int,firstName:String,lastName:String,address:String,phone:String,email: String,password: String): UserUpdateMutation.UserUpdate? {
        val response : ApolloResponse<UserUpdateMutation.Data> = apolloClient.mutation(UserUpdateMutation(
            UserInput(id = id.toDouble(), firstName = firstName, lastName = lastName, phone = phone, address = address,
                email=email, password = password))).execute()
        return response.data?.userUpdate
    }
    suspend fun  updateUserAdmin(id: Int, isActive:Boolean, rol :Boolean): UpdateAdminMutation.UserAdmin? {
        val response :ApolloResponse<UpdateAdminMutation.Data> = apolloClient.mutation(UpdateAdminMutation(
            UserUpdateAdmin(id = id.toDouble(), isActive = isActive,rol=rol))).execute()
        return response.data?.userAdmin
    }
}
