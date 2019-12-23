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

(defn get-owner
  [message]
  (:owner message))

(defn new-message
  [{text :text owner :owner sender :sender recipient :recipient}]
  {:text text
   :sender sender
   :owner (or owner sender)
   :recipient recipient})

(defn respond
  [message text]
  {:text text
   :sender (get-recipient message)
   :owner (get-owner message)
   :recipient (get-sender message)})

(defn internal?
  [message]
  (= (get-recipient message) :local))
