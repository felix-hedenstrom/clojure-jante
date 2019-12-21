(ns jante.message)

(def line "-------------------------------")

(defn get-text
  [message]
  (:text message))

(defn get-sender
  [message]
  (:sender message))

(defn get-recipient
  [message]
  (:recipient message))

(defn respond
  [message text]
  {:text text
   :sender (get-recipient message)
   :recipient (get-sender message)})

(defn print-message
  [message]
  (println line)
  (println "Sender: " (get message :sender))
  (println "Recipient: " (get message :recipient))
  (println "Text: " (get message :text))
  (println line))

