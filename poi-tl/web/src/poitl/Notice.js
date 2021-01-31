import React, { useState } from "react";
import { Row, Col, Input, Button, Descriptions, Drawer } from "antd";
const layout = {
	labelCol: {
		span: 8,
	},
	wrapperCol: {
		span: 16,
	},
};
const tailLayout = {
	wrapperCol: {
		offset: 8,
		span: 16,
	},
};
const { TextArea } = Input;
const wordCxt = {
	name: "",
	href: "http://localhost:8080/notice/word/download",
};
const Word = () => {
	return (
		<p>
			<a href={wordCxt.href + "?name=" + wordCxt.name}>{wordCxt.name}</a>
		</p>
	);
};
const Notice = () => {
	const [visible, setVisible] = useState(false);
	const [title, setTitle] = useState("");
	const [text, setText] = useState("");
	const [signature, setSignature] = useState("");
	const [year, setYear] = useState("");
	const [month, setMonth] = useState("");
	const [day, setDay] = useState("");

	const ctx = {
		title,
		text,
		signature,
		year,
		month,
		day
	}


	const showDrawer = () => {
		setVisible(true);
	};

	const closeDrawer = () => {
		setVisible(false);
	};


	const onsubmit = () => {
		let url = "http://localhost:8080/notice/tl";
		let headers = new Headers();
		headers.append("Accept", "application/json, text/plain");
		headers.append("Content-Type", "application/json;charset=UTF-8");
		let body = JSON.stringify(ctx);
		let init = {
			method: "POST",
			headers: headers,
			body,
		};
		fetch(url, init)
			.then((res) => res.text())
			.then((res) => {
				wordCxt.name = res;
				showDrawer();
			})
			.catch((e) => console.log("错误:", e));
	};

	return (
		<div>
			<Descriptions title="通知模板" column={1} bordered>
				<Descriptions.Item label="标题">
					<Input
					value = {title}
						onChange={(e) => {
							setTitle(e.target.value);
						}}
					/>
				</Descriptions.Item>
				<Descriptions.Item label="正文">
					<TextArea
						rows={8}
						onChange={(e) => {
							setText(e.target.value);
						}}
					/>
				</Descriptions.Item>
				<Descriptions.Item label="落款">
					<Row gutter={[16, 16]}>
						<Col span={2}>
							<p align="left">签名：</p>
						</Col>
						<Col span={4}>
							<Input
								onChange={(e) => {
									setSignature(e.target.value);
								}}
							/>
						</Col>
					</Row>
					<Row>
						<Col span={2}>年：</Col>
						<Col span={2}>
							<Input
								onChange={(e) => {
									setYear(e.target.value);
								}}
							/>
						</Col>
						<Col span={4}>月：</Col>
						<Col span={4}>
							<Input
								onChange={(e) => {
									setMonth(e.target.value);
								}}
							/>
						</Col>
						<Col span={4}>日：</Col>
						<Col span={4}>
							<Input
								onChange={(e) => {
									setDay(e.target.value);
								}}
							/>
						</Col>
					</Row>
				</Descriptions.Item>
			</Descriptions>
			<Button type="primary" onClick={onsubmit}>
				提交
			</Button>
			<Drawer
				title="下载文件"
				placement="bottom"
				onClose={closeDrawer}
				visible={visible}
			>
				<p>
					<Word />
				</p>
			</Drawer>
		</div>
	);
};

export default Notice;
