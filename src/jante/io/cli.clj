(ns jante.io.cli
  (:require [jante.util :refer [log]]))

(defn incoming-messages
  []
  (if-let [text (read-line)]
    (if (empty? text)
      []
      [{:sender :cli
        :recipient :local
        :text text}])))

(defn outgoing-messages
  [messages]
  (loop [messages messages]
    (if-let [message (first messages)]
      (do
        (log message)
        (recur (rest messages)))
      nil)))
