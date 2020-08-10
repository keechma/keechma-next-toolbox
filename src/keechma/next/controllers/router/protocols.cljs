(ns keechma.next.controllers.router.protocols)

(defprotocol IRouterApi
  (redirect! [this params])
  (back! [this])
  (get-url [this params]))