package com.example.librariproject.apollo

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("http://192.168.43.17:4000/graphql")
    .build()