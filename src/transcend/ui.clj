(ns transcend.ui
  (:use transcend.table-model-adapter))

(declare create-table-cell-renderer)

(defn create-table
  []
  (let [table (javax.swing.JTable. (create-table-model))
        first-col (-> (.getColumnModel table) (.getColumn 0))]
    (doto first-col
      (.setPreferredWidth 10)
      (.setResizable false))
    (doto table
      (-> .getTableHeader (.setReorderingAllowed false))
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
