CREATE user admin_tree_avl_final identified BY 'Adm1n_Avl';
CREATE DATABASE tab_tree_avl;
GRANT ALL PRIVILEGES ON tab_tree_avl.* TO 'admin_tree_avl_final'@'localhost';
flush privileges;
USER tab_tree_avl;
create table tab_image (
    id INT AUTO_INCREMENT,
    phot BLOB,
    PRIMARY KEY(id)
    );