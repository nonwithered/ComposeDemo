@file:JsModule("./ffi/console_bridge.js")
package compose.project.demo.wasm.ffi

external fun consoleLog(s: String)
