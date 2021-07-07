
plugins {
    id("idea")
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.bins"
repositories {
//    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }
    maven {
        name = "sk89q-repo"
        url = uri("https://maven.enginehub.org/repo/")
    }
    maven {
        name = "papermc-repo"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.citizensnpcs.co/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    mavenLocal()

}

dependencies {

    shadow ("org.jetbrains.kotlin:kotlin-stdlib")
//    shadow ("dev.kord:kord-core:0.7.1")
//    shadow ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
//    shadow ("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.5.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib")// https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
    implementation ("com.destroystokyo.paper:paper-api:1.16.4-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.5.0")
    implementation ("org.spigotmc:spigot:1.16.4-R0.1-SNAPSHOT")
    compileOnly ("dev.kord:kord-core:0.7.1")
    compileOnly (group = "com.comphenix.protocol", name = "ProtocolLib", version = "4.6.0")
    compileOnly (group = "com.sk89q.worldguard", name = "worldguard-bukkit", version = "7.0.4")
    compileOnly (group = "net.citizensnpcs", name = "citizensapi", version = "2.0.20-SNAPSHOT")
}


tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }


    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        from(sourceSets["main"].allSource)
    }
    val shadow by configurations.shadow
    shadowJar {
        destinationDirectory.set(file("C:/Users/a0103/바탕 화면/모음지이이입/버킷 모음지이입/1.16.5 Project RUINS 2/plugins"))
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        archiveVersion.set("")
        configurations = listOf(shadow)
    }

}
/*processResources {
    from(sourceSets.main.resources.srcDirs) {
        filter ReplaceTokens, tokens: [version: version]
    }
}*/
//jar {
//    destinationDir new File("C:/Users/a0103/바탕 화면/모음지이이입/버킷 모음지이입/1.16.5 Project RUINS 2/plugins")
//}
//shadowJar {
//    destinationDir new File("C:/Users/a0103/바탕 화면/모음지이이입/버킷 모음지이입/1.16.5 Project RUINS 2/plugins")
//}
//tasks.build.dependsOn tasks.jar
//tasks.build.dependsOn tasks.shadowJar

//idea {
//    sourceSets{
//        main.java.srcDirs += "src/main/kotlin"
//    }
//    module {
//        downloadJavadoc = true
//        downloadSources = true
//
//    }
//}
//compileKotlin {
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//}
//compileTestKotlin {
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//}
