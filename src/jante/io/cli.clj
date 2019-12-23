(ns jante.io.cli
  (:require [jante.util :refer [log]]
            [jante.message :refer [get-text get-recipient get-sender new-message]]))

(def line "-------------------------------")

(defn incoming-messages
  []
  (if-let [text (read-line)]
    (if (empty? text)
      []
      (do
        (print ">")
        (flush)
        [(new-message {:sender :cli
                       :recipient :local
                       :text text})]))))

(defn send-outgoing-messages
  [messages]
  (loop [messages messages]
    (if-let [message (first messages)]
      (do
        (log (str "\n"
                  line "\n"
                  "Sender: " (get-sender message) "\n"
                  "Recipient: " (get-recipient message) "\n"
                  "Text:\n"
                  (get-text message) "\n"
                  line))
        (recur (rest messages)))
      nil)))
