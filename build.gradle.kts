plugins {
    java
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("com.diffplug.spotless") version "6.13.0"
}

group = "top.mrxiaom"
// `version` has moved to gradle.properties

repositories {
    mavenCentral()
}
dependencies {
    // We do not allow any runtime dependencies in EvalEx
    // Lombok as 'provided' dependency. See: https://projectlombok.org/setup/gradle
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    // Dependencies only used for testing
    val junit = "5.9.0"
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${junit}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${junit}")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito:mockito-core:4.8.0")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}
tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
    withType<Javadoc> {
        (options as StandardJavadocDocletOptions).apply {
            charSet("UTF-8")
            encoding("UTF-8")
            docEncoding("UTF-8")
            addBooleanOption("Xdoclint:none", true)
        }
    }
}
spotless {
    java {
        target(
            "src/main/java/**/*.java",
            "src/test/java/**/*.java"
        )
        licenseHeaderFile(rootDir.resolve("spotless/header.txt"))
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = rootProject.group.toString()
            artifactId = rootProject.name
            version = rootProject.version.toString()

            pom {
                name = rootProject.name
                description = "EvalEx is a handy expression evaluator for Java, that allows to evaluate expressions."
                url = "https://github.com/MrXiaoM/EvalEx-j8"
                organization {
                    name = "MrXiaoM"
                    url = "https://github.com/MrXiaoM/EvalEx-j8"
                }
                licenses {
                    license {
                        name = "Apache License 2.0"
                        url = "https://github.com/ezylang/EvalEx/blob/main/LICENSE"
                    }
                }
                developers {
                    developer {
                        name = "Udo Klimaschewski"
                        organizationUrl = "https://github.com/uklimaschewski"
                    }
                    developer {
                        name = "MrXiaoM"
                        organizationUrl = "https://github.com/MrXiaoM"
                    }
                }
                scm {
                    connection = "scm:git:git@github.com:MrXiaoM/EvalEx-j8.git"
                    developerConnection = "scm:git:git@github.com:MrXiaoM/EvalEx-j8.git"
                    url = "https://github.com/MrXiaoM/EvalEx-j8"
                }
                issueManagement {
                    system = "github"
                    url = "https://github.com/MrXiaoM/EvalEx-j8/issues"
                }
            }
        }
    }
}
signing {
    val signingKey = findProperty("signingKey")?.toString()
    val signingPassword = findProperty("signingPassword")?.toString()
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications.getByName("maven"))
    }
}
nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(findProperty("MAVEN_USERNAME")?.toString())
            password.set(findProperty("MAVEN_PASSWORD")?.toString())
        }
    }
}
