import React, { useState } from "react";
import { Input, Button, Divider, Drawer } from "antd";
import { Row, Col } from "antd";

import styles from "./Notice.module.css";


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
		day,
	};

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
		<div className={styles.box}>
			<Row className={styles.top}>
				<Col className={styles.left}></Col>
				<Col className={styles.right}></Col>
			</Row>
			<Row>
				<Col className={styles.left}>标题</Col>
				<Col className={styles.right} span={10}>
					<Input
						className={styles.input}
						bordered={false}
						value={title}
						onChange={(e) => {
							setTitle(e.target.value);
						}}
					/>
				</Col>
			</Row>
			<Row className={styles.space}>
				<Col className={styles.left}></Col>
				<Col className={styles.right}></Col>
			</Row>
			<Row>
				<Col className={styles.left}>正文</Col>
				<Col className={styles.right} span={10}>
					<TextArea
						rows={8}
						onChange={(e) => {
							setText(e.target.value);
						}}
					/>
				</Col>
			</Row>
			<Row className={styles.space}>
				<Col className={styles.left}></Col>
				<Col className={styles.right}></Col>
			</Row>
			<Row>
				<Col className={styles.left}>落款</Col>
				<Col className={styles.right} span={10}>
					<div style={{ paddingBottom: "20px", width: "300px" }}>
						签名{" "}
						<Input
							className={`${styles.input}`}
							value={signature}
							bordered={false}
							onChange={(e) => {
								setSignature(e.target.value);
							}}
						/>
					</div>
					<div>
						<Input
							className={`${styles.input} ${styles.date}`}
							value={year}
							bordered={false}
							onChange={(e) => {
								setYear(e.target.value);
							}}
						/>
						年
						<Divider type="vertical" />
						<Input
							className={`${styles.input} ${styles.date}`}
							value={month}
							bordered={false}
							onChange={(e) => {
								setMonth(e.target.value);
							}}
						/>
						月 <Divider type="vertical" />
						<Input
							className={`${styles.input} ${styles.date}`}
							value={day}
							bordered={false}
							onChange={(e) => {
								setDay(e.target.value);
							}}
						/>
						日
					</div>
				</Col>
			</Row>
			<Row className={styles.space}>
				<Col className={styles.left}></Col>
				<Col className={styles.right}></Col>
			</Row>
			<Row>
				<Col className={styles.left}></Col>
				<Col className={styles.right}>
					<Button type="primary" onClick={onsubmit}>
						提交
					</Button>
				</Col>
			</Row>

			<Row className={styles.footer}>
				<Col className={styles.left}></Col>
				<Col className={styles.right}></Col>
			</Row>
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
