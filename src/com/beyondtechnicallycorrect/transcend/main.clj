(ns com.beyondtechnicallycorrect.transcend.main
  (:require [seesaw.core :refer [invoke-later frame pack! scrollable show!]]
            [com.beyondtechnicallycorrect.transcend.ui :refer [create-table]]))

(defn -main
  [& args]
  (invoke-later
    (-> (frame :title "Transcend"
          :content (scrollable (create-table))
          :on-close :exit)
      pack!
      show!)))
