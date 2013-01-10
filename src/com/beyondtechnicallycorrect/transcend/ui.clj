(ns com.beyondtechnicallycorrect.transcend.ui
  (:use com.beyondtechnicallycorrect.transcend.table-model-adapter))

(declare create-table-cell-renderer)

(def ^:private first-col-width 50)

(defn create-table
  []
  (let [table (javax.swing.JTable. (create-table-model))
        first-col (-> (.getColumnModel table) (.getColumn 0))]
    (doto first-col
      (.setMaxWidth first-col-width)
      (.setMinWidth first-col-width)
      (.setPreferredWidth first-col-width)
      (.setResizable false))
    (doto table
      (-> .getTableHeader (.setReorderingAllowed false))
      (.setAutoResizeMode javax.swing.JTable/AUTO_RESIZE_OFF)
      (.setDefaultRenderer java.lang.Object (create-table-cell-renderer))
      (.setGridColor java.awt.Color/LIGHT_GRAY))))

(defn- create-table-cell-renderer
  []
  (proxy [javax.swing.table.DefaultTableCellRenderer] []
    (getTableCellRendererComponent [table value isSelected hasFocus row col]
      (let [component (proxy-super getTableCellRendererComponent table value isSelected hasFocus row col)]
        (doto component
          (.setBackground (if (= 0 col) java.awt.Color/GRAY java.awt.Color/WHITE)))
        component))))
