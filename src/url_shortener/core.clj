(ns url-shortener.core
  (:require [ring.adapter.jetty :as ring-jetty]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [clojure.set :as set]
            [taoensso.carmine :as car :refer [wcar]])
  (:gen-class))

(defn health-check-handler [arg]
  {:status 200
   :body "URL shortener"})
(def app
  (ring/ring-handler
    (ring/router
      ["/"
       ["" {:get health-check-handler}]]
      {:data {:muuntaja m/instance
              :middleware [muuntaja/format-middleware]}})))
(defn start-web-server []
  (ring-jetty/run-jetty app {:port 3000
                             :join? false}))

(defn -main
  "Function main to start web server"
  [& args]
  (start-web-server))
