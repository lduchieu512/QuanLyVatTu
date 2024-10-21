# A0722i1-Repo-FE

1. Đường dẫn:
- Đường dẫn từ root => các module (đã làm): (/modules)
	+ / (cho home)
	+ /customers
	+ /employees
	+ /products
	+ /shipments
	+ /receipts
	+ /carts
	+ /accounts

- Đường dẫn for child:
	+ Hiện danh sách (trang chủ với home): **/
	+ Thêm mới: **/create
	+ Chỉnh sửa: **/update/:id
	+ Xem chi tiết: **/detail hoặc **/detail/:id
	+ Đăng nhập: **/login
	+ Đổi mật khẩu: **/change-password
	+ Xóa: **/delete hoặc **/delete/:id (hoặc dùng modal confirm trong list)

2. Component:
- Tên một số đối tượng trong component:
	+ private _router: Router
	+ private _activatedRoute : ActivatedRoute
	+ private _entityService: EntityService (vd: receiptService: ReceiptService)

- Đặt tên component:
	VD:
	+ Hiển thị danh sách: ng g c shipment-list (gồm searchForm [id=search-form] cho search và modal [id: delete-modal] cho xóa)
	+ Thêm mới: ng g c shipment-create (gồm mainForm [id=main-form] cho form chính)
	+ Chỉnh sửa: ng g c shipment-update (gồm mainForm [id=main-form] cho form chính)

3. Service:
- Tên đối tượng bơm vào: private _http: HttpClient

- Tên một số phương thức trong service:
	+ findAll() => trả về tập đối tượng
	+ findAllPagination() => trả về tập đối tượng có phân trang
	+ findAllBy...And...Or...() => trả về tập đối tượng (And và Or là không bắt buộc)
	+ findAllBy...And...Or...Pagination() => trả về tập đối tượng có phân trang (And và Or là không bắt buộc)
	+ findById() => trả về 1 đối tượng
	+ findBy...And...Or() => trả về 1 đối tượng (And và Or là không bắt buộc)
	+ create() 
	+ update()
	+ delete()
	+ deleteBy...And...Or...() (And và Or là không bắt buộc)
	+ getDetail()
4. Các lưu ý khác:
   - Tên thuộc tính (property): nếu là private thì để dấu _ phía trước. Vd: private _customer: Customer và thêm các getters/setters nếu cần (cách thêm tương tự như java: alt + insert)
