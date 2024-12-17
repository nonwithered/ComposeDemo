@file:JsModule("./ffi/bridge.js")
package compose.project.demo.wasm.ffi

external fun consoleLog(msg: String)

external fun windowOpen(url: String)
