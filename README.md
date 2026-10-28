<div align="center">

# 👻 No Internet Dialog

**A friendly "you're offline" dialog for Compose Multiplatform — with a ghost that refuses to stay in its grave.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.11.0-4285F4?logo=jetpackcompose&logoColor=white)](https://github.com/JetBrains/compose-multiplatform)
[![Platforms](https://img.shields.io/badge/platforms-Android%20%7C%20iOS%20%7C%20Desktop%20%7C%20Web-5D3FD3)](#-supported-platforms)

<video src="https://github.com/robertlevonyan/no-internet-dialog-kt/raw/main/media/ghost.mp4" width="420" autoplay loop muted playsinline controls>
  Your browser can't play embedded video —
  <a href="./media/ghost.mp4">watch <code>media/ghost.mp4</code></a> instead.
</video>

<sub>▶︎ If the video doesn't play inline, grab it here: <a href="./media/ghost.mp4"><code>media/ghost.mp4</code></a></sub>

</div>

---

## What is this?

Losing connectivity is the most boring moment in an app's life. `NoInternetDialog` replaces the usual grey
alert box with a small piece of theatre: a gravestone with a dead Wi-Fi signal, and a ghost that rises from
behind it, hovers, sways, drifts, and sinks back down — on a loop, on every platform.

The scene is inspired by the [**No internet connection GIF**](https://dribbble.com/shots/2887886-No-internet-connection-GIF)
shot on Dribbble — rebuilt from scratch in Compose rather than shipped as a GIF. Everything is drawn in code:
no GIF, no Lottie file, no video at runtime. The ghost is a vector authored as Kotlin path data, animated with
Compose keyframes, and rippled by a runtime shader.

## ✨ Highlights

- **One composable, five platforms.** Android, iOS, Desktop (JVM), and Web via both Wasm and JS.
- **No animation assets.** The ghost is vector paths plus `rememberInfiniteTransition` — nothing to decode, nothing to ship.
- **A real runtime shader.** A jelly-like wobble written in AGSL / SkSL, applied through `RenderEffect`.
- **Graceful degradation.** On Android below 13 the shader is a no-op; the ghost still rises, hovers and sways.
- **Dialog or full screen.** Use the packaged dialog, or drop the artwork straight into your own offline screen.

## 📱 Supported platforms

| Platform | Kotlin target | Notes |
|---|---|---|
| Android | `androidLibrary` | `minSdk 24`; wobble shader needs API 33+ |
| iOS | `iosArm64`, `iosSimulatorArm64` | Static `Shared` framework |
| Desktop | `jvm()` | JVM 11 bytecode |
| Web (Wasm) | `wasmJs { browser() }` | Faster, modern browsers |
| Web (JS) | `js { browser() }` | Broader browser support |

Apple, Desktop and both Web targets share a single `skikoMain` source set, so the Skia-backed shader code is
written exactly once.

## 🚀 Usage

Show it as a dialog when connectivity drops:

```kotlin
var offline by remember { mutableStateOf(false) }

if (offline) {
    NoInternetDialog(onDismissRequest = { offline = false })
}
```

Or use the artwork on its own — it fills the space it's given and paints its own gradient background, so it
works as a full-screen empty state:

```kotlin
@Composable
fun OfflineScreen() {
    NoInternetContent()
}
```

### Public API

| Declaration | Description |
|---|---|
| `NoInternetDialog(onDismissRequest: () -> Unit)` | The artwork inside a dismissible `Dialog` + `Card`. |
| `NoInternetContent()` | Just the animated scene — gradient, gravestone, dead signal, ghost. |

> [!NOTE]
> The library isn't published to Maven Central yet, so there are no dependency coordinates to add.
> For now, consume the `:shared` module directly or clone and run the samples below.

## 🎬 How the ghost works

The whole scene is one `rememberVectorPainter`. The ghost's body and face are `Path` nodes inside a single
`Group`, and four `animateFloat` channels drive that group's transform on a synchronised ~4.5 s loop:

| Channel | Does |
|---|---|
| `translationY` | Rises from behind the stone, holds, then sinks back into the ground |
| `scale` | Grows `0.9 → 2.0` on the way up, so the ghost reads as coming *toward* you |
| `rotation` | A pendulum sway around a pivot below the tail |
| `translationX` | Pops up on the right, then drifts left across the stone |

Two details are worth knowing before you tweak the numbers:

- **The canvas is wider than the artwork.** The ghost's paths live on a 512-unit square, but the drift carries
  it about 30 units past the left edge. A `VectorPainter` clips to its viewport, so the canvas is padded 56
  units on the left — without that, the ghost's left arm is sliced off flat mid-drift.
- **The bottom edge is the ground.** Clipping there is deliberate: it's what swallows the ghost between
  cycles, hidden behind the mound. Don't pad it, or the tail pokes out while the ghost is supposed to be gone.

The wobble is a runtime shader behind an `expect`/`actual` modifier — `RuntimeShader` + `RenderEffect` on
Android, `RuntimeShaderBuilder` + `ImageFilter` on Skia platforms. It samples the layer 1:1 and offsets it by
a travelling ripple; the canvas padding absorbs the ~1% the ripple reaches outside the layer.

## 🗂 Project structure

```
shared/src/                  the library
├── commonMain/
│   ├── kotlin/              NoInternetDialog, NoInternetContent, ghost paths, shader source
│   └── composeResources/    gravestone + ghost drawables
├── skikoMain/               wobble via Skia RuntimeShaderBuilder  (iOS + Desktop + Web)
├── androidMain/             wobble via android.graphics.RenderEffect
└── iosMain/                 MainViewController entry point

androidApp/                  sample Android app
desktopApp/                  sample Desktop app
webApp/                      sample Web app (Wasm + JS)
iosApp/                      sample iOS app (Xcode project)
media/ghost.mp4             the recording at the top of this file
```

## 🛠 Running the samples

```bash
# Android
./gradlew :androidApp:assembleDebug

# Desktop
./gradlew :desktopApp:run
./gradlew :desktopApp:hotRun --auto      # with hot reload

# Web
./gradlew :webApp:wasmJsBrowserDevelopmentRun    # Wasm — faster
./gradlew :webApp:jsBrowserDevelopmentRun        # JS  — wider support
```

For iOS, open [`iosApp`](./iosApp) in Xcode and run it there.

## 📋 Requirements

- Gradle 9.5.1 — bundled as a wrapper, so `./gradlew` needs no local install
- Android Studio / IntelliJ IDEA with the Kotlin Multiplatform plugin
- macOS with Xcode, for the iOS target

## 🧭 Status

Early days — the animation is in place and runs everywhere, and the API surface is intentionally tiny while
it settles.

- [x] Ghost animation across Android, iOS, Desktop, Wasm and JS
- [x] Runtime-shader wobble with a graceful fallback
- [ ] Theming hooks — colors, sizing, custom copy
- [ ] Automatic connectivity detection per platform
- [ ] Publish to Maven Central
- [ ] Add a `LICENSE` file

## 🙏 Credits

- Animation design inspired by [**No internet connection GIF**](https://dribbble.com/shots/2887886-No-internet-connection-GIF)
  on Dribbble. The artwork and animation here are an original Compose implementation of that idea — the timing
  of the rise, hover, drift and sink follows the original's ~4.5 s loop.
- Built with [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) and
  [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform).

  Happy Coding!

## 👤 Author

**Robert Levonyan** — [@robertlevonyan](https://github.com/robertlevonyan)


<div align="center">
<sub>No connection, no problem — at least the ghost is having fun. 👻</sub>
</div>
