# StackOverflow Badges

A simple StackOverFlow client that shows the user badges from StackOverFlow.

| Portrait | Landscape |
|-|-|
|<img src="https://github.com/sdeira/StackOverflowBadges/blob/main/screenshots/portrait.png" width="200">|<img src="https://github.com/sdeira/StackOverflowBadges/blob/main/screenshots/landscape.png" width="200">|

## Architecture
- MVVM

### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Hilt][hilt] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Room][room] persistence library
* [Paging][paging] for paging
* [Detekt][detekt] for static code analysis

[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[data-binding]: https://developer.android.com/topic/libraries/data-binding/index.html
[hilt]: https://dagger.dev/hilt/
[retrofit]: http://square.github.io/retrofit
[room]: https://developer.android.com/topic/libraries/architecture/room
[paging]: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
[glide]: https://github.com/bumptech/glide
[detekt]: https://detekt.github.io/detekt/

## Getting Started

```shell
$ git clone git@github.com:sdeira/StackOverflowBadges.git
```

To run detekt

```shell
$ ./gradlew detekt
```