package org.photoexp

class Artist {
    String name
    Integer numberOfPics

    static constraints = {
        name(blank: false)
        numberOfPics(blank: false)
    }

    static mapping = {
        collection "artists"
        database "mongo"
    }
}
