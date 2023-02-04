(ns run.test
  (:use [clojure pprint]))

(defn build-stack
  []
  (atom []))

(defn add!
  [data stack]
  (swap! stack conj data))

(defn drop!
  [last-item]
  (vec (drop-last last-item)))

(defn rm!
  [stack]
  (swap! stack drop!))

(defn init
  []
  (let [stack (build-stack)]
    (add! "foo" stack)
    (add! "bar" stack)
    (add! "happy" stack)
    (println @stack)
    (rm! stack)
    (println @stack)
    (rm! stack)
    (println @stack)))

(init)


