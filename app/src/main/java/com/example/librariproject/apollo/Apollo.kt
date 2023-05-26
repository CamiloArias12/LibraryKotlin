package com.example.librariproject.apollo

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("http://192.168.0.14:8080/graphql")
    .build()